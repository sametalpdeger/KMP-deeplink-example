package org.example.deeplink

import java.io.File
import java.util.concurrent.TimeUnit


fun String.runCommand(workingDir: File) {
    ProcessBuilder(*split(" ").toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
        .waitFor(60, TimeUnit.MINUTES)
}


fun configureDeeplink() {
    val os = System.getProperty("os.name").lowercase()

    if (os.contains("win")) {
        println("TODO: Configure deeplink for Windows")
        return
    }

    if (os.contains("linux")) {
        println("Detected Linux, configuring deeplink")
        val homeDir = System.getProperty("user.home")
        val desktopFile = File("$homeDir/.local/share/applications/deeplink.desktop")

        if (desktopFile.exists()) {
            println("Removing existing deeplink.desktop")
            desktopFile.delete()
        }

        desktopFile.writeText(
            "[Desktop Entry]\n" +
                    "Type=Application\n" +
                    "Name=deeplink\n" +
                    "Exec=/opt/org.example.deeplink/bin/org.example.deeplink %u\n" +
                    "NoDisplay=true\n" +
                    "Terminal=true\n" +
                    "Categories=Application;\n" +
                    "MimeType=x-scheme-handler/deeplink;"
        )

        // Explicitly use the home directory variable
        "update-desktop-database $homeDir/.local/share/applications/".runCommand(File("/"))
        return
    }
}
