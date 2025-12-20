package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class VideoPlayer {
    private VideoPlayer() {}

    public static void playVideo(String path, int secondsToWait) {
        File videoFile = new File(path);
        System.out.println("Trying: " + videoFile.getAbsolutePath());

        if (!videoFile.exists()) {
            System.out.println("Видео не найдено: " + path);
            return;
        }

        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("mac")) {
                playQuickTimeAuto(videoFile);
            } else if (os.contains("win")) {
                playWindowsAuto(videoFile);
            } else {
                playLinuxFallback(videoFile);
            }

            Thread.sleep(secondsToWait * 1000L);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private static void playQuickTimeAuto(File videoFile) throws IOException {
        String path = videoFile.getAbsolutePath()
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");

        String script =
                "tell application \"QuickTime Player\"\n" +
                        "activate\n" +
                        "open POSIX file \"" + path + "\"\n" +
                        "delay 0.2\n" +
                        "play document 1\n" +
                        "end tell";

        new ProcessBuilder("osascript", "-e", script).start();
    }

    private static void playWindowsAuto(File videoFile) throws IOException {
        String abs = videoFile.getAbsolutePath();

        String vlcPath = findVlcOnWindows();
        if (vlcPath != null) {
            new ProcessBuilder(
                    vlcPath,
                    "--play-and-exit",
                    abs
            ).start();
            return;
        }

        String cmd = "start \"\" \"" + abs + "\"";
        new ProcessBuilder("cmd", "/c", cmd).start();

        System.out.println("VLC не найден. Открыл через плеер по умолчанию (автовоспроизведение зависит от него).");
    }

    private static String findVlcOnWindows() {
        List<String> candidates = new ArrayList<>();

        String pf = System.getenv("ProgramFiles");
        String pf86 = System.getenv("ProgramFiles(x86)");
        String localAppData = System.getenv("LOCALAPPDATA");

        if (pf != null) candidates.add(pf + "\\VideoLAN\\VLC\\vlc.exe");
        if (pf86 != null) candidates.add(pf86 + "\\VideoLAN\\VLC\\vlc.exe");
        if (localAppData != null) candidates.add(localAppData + "\\Programs\\VideoLAN\\VLC\\vlc.exe");

        for (String c : candidates) {
            if (c != null && new File(c).exists()) return c;
        }

        try {
            Process p = new ProcessBuilder("where", "vlc").start();
            int code = p.waitFor();
            if (code == 0) return "vlc";
        } catch (Exception ignored) {}

        return null;
    }

    // -------- Linux fallback --------
    private static void playLinuxFallback(File videoFile) throws IOException {
        new ProcessBuilder("xdg-open", videoFile.getAbsolutePath()).start();
        System.out.println("Открыл через xdg-open (автовоспроизведение зависит от плеера).");
    }
}