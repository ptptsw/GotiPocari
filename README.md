# Madcamp - GotiPocari
Android project for week 1 of Madcamp@KAIST


# GotiPocari Application의 구성
**1. Activity (2개)**
   * MainActivity
   * LoadingActivity

**2. Fragment (4개)**
   * Dial Fragment
   * PhoneBook Fragment
   * Gallery Fragment
   * RandomGame Fragment - (In App version / AR version)

**3. Control Flow**
   * Application 구동 시 MainActivity에서 LoadingActivity를 일정시간 실행시켜 로딩화면을 실행합니다.
   ![Loading](https://github.com/geonsikSeo/GotiPocari_Project1/blob/master/imageformd/loadingimage.png)


   * LoadingActivity의 startLoading() 함수에서 시간이 종료되면 MainActivity에서 activity_main.xml을 실행합니다.
   ![main](https://github.com/geonsikSeo/GotiPocari_Project1/blob/master/imageformd/main.png)

   * Navigation Bar를 Tap하여 위 2. Fragment에서 설명한 각 Fragment로 이동이 가능합니다.



# Dial Fragment 구현 사항

**1)기능**

ㅇㅇㅇㄻㄹㅈㄷㄹㅈㄷㄹ

**2) 구현 코드**

```
 @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backspace) {
            if (count.equals(""))
                return;
            count = count.substring(0, count.length() - 1);
        } else if (v.getId() == R.id.btext) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SEND_SMS);
            else {
                Intent text = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + count));
                startActivity(text);
            }
        } else if (v.getId() == R.id.bcall) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.CALL_PHONE }, PERMISSIONS_CALL_PHONE);
            else {
                Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+count));
                startActivity(call);
            }
        } else {
            count += Integer.toString(v.getId() - R.id.b0);
        }
        text.setText(count);
    }
```



# PhoneBook Fragment 구현 사항 

   
