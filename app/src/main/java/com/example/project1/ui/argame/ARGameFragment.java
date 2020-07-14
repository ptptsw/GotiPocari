package com.example.project1.ui.argame;

import android.gesture.Gesture;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project1.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.security.Permission;
import java.util.function.Consumer;
import java.util.function.Function;

public class ARGameFragment extends Fragment {
    private static final int RC_PERMISSIONS = 1;
    private ArSceneView arSceneView;
    private ModelRenderable bottleRenderable;
    private GestureDetector gestureDetector;
    private boolean loadingComplete;
    private boolean bottlePlaced;
    private boolean cameraPermissionRequested;
    private RotatingNode wineBottle;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_argame, container, false);

        if (!PermissionUtils.checkIsSupportedDeviceOrFinish(this.getActivity()))
            return root;

        arSceneView = root.findViewById(R.id.ar_scene_view);
        ModelRenderable.builder().setSource(this.getContext(), R.raw.champagne)
                .build()
                .thenAccept(new Consumer<ModelRenderable>() {
                    @Override
                    public void accept(ModelRenderable modelRenderable) {
                        ARGameFragment.this.bottleRenderable = modelRenderable;
                        ARGameFragment.this.loadingComplete = true;
                    }
                })
                .exceptionally(new Function<Throwable, Void>() {
                    @Override
                    public Void apply(Throwable throwable) {
                        Toast toast = Toast.makeText(ARGameFragment.this.getContext(), "Unable to load renderable", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    }
                });

        gestureDetector = new GestureDetector(this.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                onSingleTap(e);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });

        arSceneView.getScene().setOnTouchListener(new Scene.OnTouchListener() {
            @Override
            public boolean onSceneTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
                if (!bottlePlaced)
                    return gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });

        arSceneView.getScene().setOnTouchListener(new Scene.OnTouchListener() {

            @Override
            public boolean onSceneTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
                if (!bottlePlaced)
                    return gestureDetector.onTouchEvent(motionEvent);

                return false;
            }
        });

        PermissionUtils.requestCameraPermission(this.getActivity(), RC_PERMISSIONS);

        return root;
    }

    private void onSingleTap(MotionEvent e) {
        if (!loadingComplete)
            return;

        Frame frame = arSceneView.getArFrame();

        if (frame != null && !bottlePlaced && placeBottleSuccessful(e, frame))
            bottlePlaced = true;
    }

    private boolean placeBottleSuccessful(MotionEvent tap, Frame frame) {
        if (tap != null && frame.getCamera().getTrackingState() == TrackingState.TRACKING) {
            for (HitResult hit : frame.hitTest(tap)) {
                Trackable trackable = hit.getTrackable();

                if (trackable instanceof Plane && ((Plane)trackable).isPoseInPolygon(hit.getHitPose())) {
                    Anchor anchor = hit.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arSceneView.getScene());
                    wineBottle = new RotatingNode();
                    wineBottle.setRenderable(bottleRenderable);
                    anchorNode.addChild(wineBottle);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (arSceneView == null)
            return;

        if (arSceneView.getSession() == null) {
            try {
                Config.LightEstimationMode lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR;
                Session session = cameraPermissionRequested ?
                        PermissionUtils.createArSessionWithInstallRequest(this.getActivity(), lightEstimationMode) :
                        PermissionUtils.createArSessionNoInstallRequest(this.getActivity(), lightEstimationMode);
                if (session == null) {
                    cameraPermissionRequested = PermissionUtils.hasCameraPermission(this.getActivity());
                    return;
                } else {
                    arSceneView.setupSession(session);
                }
            } catch (UnavailableException e) {
                PermissionUtils.handleSessionException(this.getActivity(), e);
            }
        }
        try {
            arSceneView.resume();
        } catch (CameraNotAvailableException ex) {
            PermissionUtils.displayError(this.getContext(), "Unable to get camera", ex);
            this.getActivity().finish();
            return;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (arSceneView != null)
            arSceneView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (arSceneView != null)
            arSceneView.destroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        if (!PermissionUtils.hasCameraPermission(this.getActivity())) {

            if (!PermissionUtils.shouldShowRequestPermissionRationale(this.getActivity()))
                PermissionUtils.launchPermissionSettings(this.getActivity());
            else
                Toast.makeText(this.getActivity(), "Camera permission is needed to run this fragment", Toast.LENGTH_LONG).show();
            this.getActivity().finish();
        }
    }
}
