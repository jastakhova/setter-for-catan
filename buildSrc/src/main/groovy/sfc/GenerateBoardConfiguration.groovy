package sfc;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author noel.yap@gmail.com
 */
class GenerateBoardConfiguration extends DefaultTask {
    // TODO: specify inputs

    String boardConfigurationType

    @TaskAction
    void apply() {
        final boardConfigurationName = camelCaseToHyphenated(boardConfigurationType[0].toLowerCase() + boardConfigurationType.substring(1))

        final classpath = project.configurations.compile.inject(project.tasks.jar.archivePath.toString()) { String accum, File file ->
                "${accum}:${file}"
        }
        final env = [
        "CLASSPATH=${classpath}",
                "JAVA_HOME=${System.getenv('JAVA_HOME')}",
                "SCALA_HOME=${System.getenv('SCALA_HOME')}"
        ]

        final command = ["${project.projectDir}/setter-for-catan.scala", boardConfigurationName]
        final process = command.execute(env, null)

        println (process.text)
    }

    private static String camelCaseToHyphenated(final camelCasedString) {
        int index = indexOfUpperCaseLetter(camelCasedString, 0)
        if (index == -1) {
            camelCasedString
        } else {
            String result = ''

            def range = 0..<index
            while (index != -1) {
                result += camelCasedString[range] + '-' + camelCasedString[index].toLowerCase()
                index = indexOfUpperCaseLetter(camelCasedString, index + 1)
                range = range.to+2..<index
            }

            result + camelCasedString.substring(range.to as int)
        }
    }

    private static int indexOfUpperCaseLetter(String camelCasedString, int start) {
        int index = camelCasedString.substring(start).findIndexOf { letter ->
                def upperCaseLetters = 'A'..'Z'

            upperCaseLetters.contains(letter)
        }

        (index == -1) ? index : start + index
    }
}