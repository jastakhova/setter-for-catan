package sfc

import org.gradle.api.tasks.TaskAction

/**
 * @author noel.yap@gmail.com
 */
class GenerateValidBoardConfigurationTask extends SetterForCatanTask {
    @TaskAction
    void apply() {
        println setterForCatan().execute(boardConfigurationType)
    }
}