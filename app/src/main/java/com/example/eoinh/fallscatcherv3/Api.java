package com.example.eoinh.fallscatcherv3;

/**
 * Created by EoinH on 28/01/2018.
 */

public class Api {
//    private static final String ROOT_URL = "http://10.52.54.189/FallsApi/v1/Api.php?apicall=createfall";
//    public static final String URL_CREATE_FALL = ROOT_URL + "createfall";
//    public static final String URL_READ_FALLS = ROOT_URL + "getfalls";
//    public static final String URL_UPDATE_FALL = ROOT_URL + "updatefalls";
//    public static final String URL_DELETE_FALL = ROOT_URL + "deletefalls&id=";
    private static final String ROOT_URL = "http://10.52.2.122/sync2/v1/Api.php?apicall=";

    public static final String URL_CREATE_HERO = ROOT_URL + "createFall";
    public static final String URL_READ_HEROES = ROOT_URL + "getheroes";
    public static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";
    public static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";
}
