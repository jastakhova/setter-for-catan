package sfc

/**
 * @author noel.yap@gmail.com
 */
class SetterForCatan {
    def configuration
    def library
    def executable

    String execute(String boardConfigurationType, List<String> extraArgs=[]) {
        final boardConfigurationName =
            camelCaseToHyphenated(boardConfigurationType[0].toLowerCase() + boardConfigurationType.substring(1))

        final classpath = configuration.inject(library.toString()) { String accum, File file ->
            "${accum}:${file}"
        }
        final env = [
                "CLASSPATH=${classpath}",
                "JAVA_HOME=${System.getenv('JAVA_HOME')}",
                "SCALA_HOME=${System.getenv('SCALA_HOME')}"
        ]

        final command = [executable.toString(), boardConfigurationName]
        command.addAll(extraArgs)

        final process = command.execute(env, null)

        process.text
    }

    void createScript(File script) {
        final classpath = configuration.inject(library.toString()) { String accum, File file ->
            "${accum}:${file}"
        }

        script << ('#!/bin/bash\n')
        script << ('\n')
        script << ("export CLASSPATH=${classpath}\n")
        script << ("export JAVA_HOME=${System.getenv('JAVA_HOME')}\n")
        script << ("export SCALA_HOME=${System.getenv('SCALA_HOME')}\n")
        script << ('\n')
        script << ("${executable} \"\$@\"\n")
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
