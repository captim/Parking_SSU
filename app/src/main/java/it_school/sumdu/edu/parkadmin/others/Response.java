package it_school.sumdu.edu.parkadmin.others;


public interface Response<T> {
    void onSuccess(T data);
    void onFailure(String message);
}
