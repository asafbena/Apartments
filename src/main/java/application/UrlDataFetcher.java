package application;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;

import static application.Constants.areas;
import static application.Constants.areasToLink;


public class UrlDataFetcher {
//    public static String url = "https://www.fakenamegenerator.com/";
    private static UrlDataFetcher urlDataFetcher = null;
    private static final Desktop desktop= Desktop.getDesktop();
    private static final Map<String, List<Integer>> areasNameToKeyEvent = buildAreasNameToKeyEvent();

    private static Map<String, List<Integer>> buildAreasNameToKeyEvent() {
        Map<String, List<Integer>> areasNameToKeyEvent = new HashMap<>();
        areas.stream().forEach(area -> areasNameToKeyEvent.put(area, convertStringToKeysEvent(area)));
        return areasNameToKeyEvent;
    }

    private static List<Integer> convertStringToKeysEvent(String area) {
        List<Integer> areaNameKeysEvent = new ArrayList<>();
        for (int i = 0; i < area.length(); i++){
            int keyCode = java.awt.event.KeyEvent.getExtendedKeyCodeForChar(area.charAt(i));
            areaNameKeysEvent.add(keyCode);
        }
        return areaNameKeysEvent;
    }


    private UrlDataFetcher() {
        
    }

    public static UrlDataFetcher newInstance() {
        if (urlDataFetcher == null)
            urlDataFetcher = new UrlDataFetcher();
        return urlDataFetcher;
    }


    public void downloadApartments(){

        List<String> parallelList=new ArrayList<>();

        for (int i = 0; i < areas.size(); i++) {
//            parallelList.add(areas.get(i));
            String areaName = areas.get(i);
            String areaLink = areasToLink.get(areaName);
            downloadPageData(areaName, areaLink);
            moveDownloadedPageData(areaName);
        }


//        parallelList.parallelStream().forEach((areaName) -> {
//                String areaLink = areasToLink.get(areaName);
//                downloadPageData(areaName, areaLink);
//                moveDownloadedPageData(areaName);
//        });

    }

    private void moveDownloadedPageData(String areaName) {
        String file_to_move = "/Users/asafbenavraham/Downloads/" + areaName + ".json";
        String resource_folder = "/Users/asafbenavraham/IdeaProjects/Apartments/src/main/resources/areas/" + areaName + ".json";
        Path fileToMovePath = Paths.get(file_to_move);
        Path targetPath = Paths.get(resource_folder);
        try {
            Files.move(fileToMovePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String downloadPageData(String areaName, String areaLink){

        try {
            Robot robot = new Robot();
            desktop.browse(new URI(areaLink));
            Thread.sleep(6000);
            robot.keyPress(KeyEvent.VK_META);
            Thread.sleep(100);
            robot.keyPress(KeyEvent.VK_S);
            Thread.sleep(200);
            robot.keyRelease(KeyEvent.VK_META);
            robot.keyRelease(KeyEvent.VK_S);
            Thread.sleep((200));
            typeAreaName(robot, areaName);

            robot.keyPress(KeyEvent.VK_ENTER);
            Thread.sleep((200));
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_ENTER);
            Thread.sleep((200));
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(300);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

    private void typeAreaName(Robot robot, String areaName) {
        List<Integer> keysEvent = areasNameToKeyEvent.get(areaName);
        keysEvent.stream().forEach(keyEvent -> {
            robot.keyPress(keyEvent);
            robot.keyRelease(keyEvent);
        });

    }

}
