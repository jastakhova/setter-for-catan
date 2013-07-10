package sfc

import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * @author noel.yap@gmail.com
 */
class CreateScriptTask extends SetterForCatanTask {
    @OutputFile
    def script = new File(project.buildDir, 'setter-for-catan.sh')

    @TaskAction
    void apply() {
        setterForCatan().createScript(script)
    }
}
