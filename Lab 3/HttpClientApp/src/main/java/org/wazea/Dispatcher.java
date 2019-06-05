package org.wazea;

class Dispatcher {

    static void makeRequest(String type) {

        HttpClient client = new HttpClient();
        switch (type) {
            case "Get" :
                client.httpGet();
                break;

            case "Head" :
                client.httpHead();
                break;

            case "Post" :
                client.httpPost();
                break;

            case "Put" :
                client.httpPut();
                break;

            case "Patch" :
                client.httpPatch();
                break;

            case "Delete" :
                client.httpDelete();
                break;

            default :
                System.out.println("Unknown request type!!");
                break;
        }
    }
}
