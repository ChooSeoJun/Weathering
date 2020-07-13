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

        information.add(new Information("비가 오면 향긋한\n 냄새가 나는 이유를 아시나요?","'페트리코'라고도 부르는 이 냄새는 박테리아, 식물, 번개가 모두 상호작용하여 독특한 화학 반응이 일어나기에 생겨납니다."));
        information.add(new Information("소나기가 주로 오후에 오는\n 이유를 아시나요?","소나기는 상층의 차가운 공기와 하층의 더운공기의 온위차가 클 경우 대류현상을 활발하게 일으켜 내리게됩니다. 온위차가 크기 위해선 지표면의 가열이 필요하므로 지표면이 가열되어 온도가 가장 높은 시간대인 오후에 주로 소나기가 내립니다."));
        information.add(new Information("번개가 왜 치는 걸까요?","소나기가 내리는 원리를 기억하시죠? 그 중 대류현상이 일어나는 시점에 따듯한 공기가 상층으로 이동할 때 상승기류가 생기게 됩니다. 이때, 얼음결정과 물결정이 바람같은 요인들과 부딪혀 전기를 띕니다. 구름하부는 무거운 물로 이루어져 음전하를 띄고 구름상부는 양전하를 띄게 됩니다. 서로 다른 전하가 부딪혀 상상을 뛰어넘는 에너지의 불꽃을 만드는데 이것이 번개입니다."));
    }
    public static NotificationData getInstance() { return instance; }
    public List<Information> getInformation() { return information; }
//    public void setInformation(List<Information> informationData) {
//        this.information = informationData;
//    }
}