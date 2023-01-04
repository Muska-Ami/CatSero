package moe.xmcn.catsero;

import moe.xmcn.catsero.utils.HttpClient;

public class TestGetFileProtocol {
    public static void main(String[] args) {
        new HttpClient().getRequest("file://E:/Downloads/database.json");
    }
}
