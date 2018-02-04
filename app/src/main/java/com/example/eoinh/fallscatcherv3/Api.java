package com.example.eoinh.fallscatcherv3;

/**
 * Created by EoinH on 28/01/2018.
 */

public class Api {
    private static final String ROOT_URL = "http://10.52.2.72/FallsApi/v1/Api.php?apicall=";

    public static final String URL_CREATE_FALL = ROOT_URL + "createfall";
    public static final String URL_READ_FALLS = ROOT_URL + "getfalls";
    public static final String URL_UPDATE_FALL = ROOT_URL + "updatefalls";
    public static final String URL_DELETE_FALL = ROOT_URL + "deletefalls&id=";

}
