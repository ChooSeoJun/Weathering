package com.csj.weathering.data;

import java.util.ArrayList;
import java.util.List;

public class NotificationData {
    private static NotificationData instance = new NotificationData();
    private List<Information> information;
    private boolean isLoaded = false;
    private NotificationData(){
        if(isLoaded) return;

        information = new ArrayList<>();

        information.add(new Information("비가 오면 향긋한 냄새가 나는 이유를 아시나요?","'페트리코'라고도 부르는 이 냄새는 박테리아, 식물, 번개가 모두 상호작용하여 독특한 화학 반응이 일어나기에 생겨납니다."));
        information.add(new Information("",""));
        information.add(new Information("",""));
        information.add(new Information("",""));
        information.add(new Information("",""));
        information.add(new Information("",""));
        information.add(new Information("",""));
        information.add(new Information("",""));
        information.add(new Information("",""));
    }
    public static NotificationData getInstance() { return instance; }
    public List<Information> getInformation() { return information; }
//    public void setInformation(List<Information> informationData) {
//        this.information = informationData;
//    }
}