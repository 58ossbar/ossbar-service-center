package com.ossbar.modules.evgl.tch.dto;


import java.io.Serializable;
import java.util.List;

public class SaveClassTraineeDTO implements Serializable {

    private String classId;

    private List<String> traineeIdList;

    public SaveClassTraineeDTO() {
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public List<String> getTraineeIdList() {
        return traineeIdList;
    }

    public void setTraineeIdList(List<String> traineeIdList) {
        this.traineeIdList = traineeIdList;
    }
}
