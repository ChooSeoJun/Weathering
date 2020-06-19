package com.csj.weathering;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<String> city = new MutableLiveData<>(); // 감시하다가 값 변경 일어나면 메세지를 보내줌 - final로 해도 됨 ( 이 자체로 값을 저장하는 애가 아님 )
//    private final MutableLiveData<Weather> selected = new MutableLiveData<>();

    public void setCity(String city){this.city.setValue(city);}
    public LiveData<String> getCity(){return city;} // LiveData로 받아야 감시를 붙일 수 있음, String으로 하면 그냥 String 값만 get

//    public void setSelected(Weather weather){this.selected.setValue(weather);}
//    public LiveData<Weather> getSelected(){return selected;}
}
