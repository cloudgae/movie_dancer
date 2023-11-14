package com.example.moovit_dancer;

public class DanceClass {
    private int classId;
    private String className;
    private String mozip;
    private String classDate;

    // 기본 생성자
    public DanceClass() {
        // Firebase Firestore에서 객체를 역직렬화할 때 필요한 기본 생성자
    }

    // 매개변수가 있는 생성자
    public DanceClass(int classId, String className, String mozip) {
        this.classId = classId;
        this.className = className;
        this.mozip = mozip;
//        this.classDate = classDate;
    }

    // Getter 및 Setter 메서드
    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMozip() {
        return mozip;
    }

    public void setMozip(String mozip) {
        this.mozip = mozip;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }
}
