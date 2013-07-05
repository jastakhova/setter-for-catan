package sfc

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles

/**
 * @author noel.yap@gmail.com
 */
class SetterForCatanTask extends DefaultTask {
    @InputFiles
    def configuration = project.configurations.compile

    @InputFile
    def executable = new File("${project.projectDir}/setter-for-catan.scala")

    @InputFile
    def library = project.tasks.jar.archivePath

    String boardConfigurationType

    SetterForCatan setterForCatan() {
        new SetterForCatan(configuration: configuration, library: library, executable: executable)
    }
}
