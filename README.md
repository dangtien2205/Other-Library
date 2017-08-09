# Other Library


## How to use
If you prefer it, you can use the gradle dependency, you have to add these lines in your build.gradle file:
```  
repositories {
    jcenter()
}

dependencies {
    compile 'com.otherlibrary.tiplibrary:otherlibrary:1.1.1'
}

```  

## 1. SharedPreferences
```  
AppPref mAppPref= AppPref.getPref(context); 
mAppPref.putString(Constant.TEXT,"Hello");
String name =mAppPref.getString(Constant.TEXT,"");
``` 

## 2. MyApp

You need create file MyApp.java .Then add code 
```  
public class MyApp extends App {
}
``` 
Then edit manifest :
```  
 <application
        android:name=".MyApp"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
...
``` 

## 3. Check Service
```  
OtherMethor.checkService(context,Your Service's ClassName);

Ex: OtherMethor.checkService(context,context.getPackageName+".MyService");
```
 
## 4. Check Permission Service on device XiaoMi and Huawei
```  
OtherMethor.checkPermission(context);
``` 

## 5. Click 3D Touch
```  
OtherMethor.click3DTouch(context ,time_delay ,new OtherMethor.OnClick() {
            @Override
            public void click3DTouch() {
                //TO-DO
                Toast.makeText(MainActivity.this,"3D touch",Toast.LENGTH_SHORT).show();
            }
});
``` 

## 6. Other App

**Open Introduction :**
```  
ArrayList<Integer> arrayList = new ArrayList<>();
arrayList.add(R.drawable.bg1);
arrayList.add(R.drawable.bg2);
arrayList.add(R.drawable.bg3);
arrayList.add(R.drawable.bg4);
arrayList.add(R.drawable.bg5);
IntroductionActivity.startIntroduction(this,arrayList);
```  
![Alt Text](./screenshots/bg1.jpg)

**Dialog rate app :**
```  
DialogFiveStars fiveStarsDialog = new DialogFiveStars(context, "");
DialogFiveStarsNotFinish  dialogFiveStarsNotFinish = new DialogFiveStarsNotFinish(context, "");
``` 
When onBackPressed() you use code 
```  
  private int numBack=1;
  @Override
    public void onResume() {
       super.onResume();
       numBack=1;
    }
  @Override
    public void onBackPressed() {
        if (numBack == 1) {
            numBack--;
            Toast.makeText(this, "Double click to quit", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!fiveStarsDialog.isRate()) {
            fiveStarsDialog.show();
        } else {
            super.onBackPressed();
        }
    }
``` 
When onClick() you use code:
 ```  
dialogFiveStarsNotFinish.show();
``` 

**Feed back  :**
```  
OtherMethor.sentEmail(context,Your_Email);
``` 

**Privacy Policy  :**
```  
OtherMethor.privacyPolicy(context,Your_Web);
``` 
# Thanks. Thanks for coming
