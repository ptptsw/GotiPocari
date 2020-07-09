package com.example.project1.ui.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.project1.R;
import com.example.project1.ui.phonebook.Adapter;
import com.example.project1.ui.phonebook.JsonData;
import com.example.project1.ui.phonebook.PhoneBookViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchAdapter searchadapter;
    private Adapter adapter;
    private List<JsonData> list;
    private ListView listView;
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private ArrayList<JsonData> listViewItemList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_phonebook, container, false);

        adapter = new Adapter();
        editSearch = (EditText) root.findViewById(R.id.editSearch);
        listView = (ListView) root.findViewById(R.id.listView);

        list = adapter.getListViewItemList();
        listViewItemList = adapter.getListViewItemList();
        searchadapter = new SearchAdapter(listViewItemList, this.getContext());
        System.out.println("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"+list.size());

        //TODO: 여기 지금 안됌. list에 json 데이터 안들어가고 있음.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });



        return root;
    }



    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(listViewItemList);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < listViewItemList.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (listViewItemList.get(i).getName().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(listViewItemList.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }
}
