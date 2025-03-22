package io.github.truepadawan.ktlint.problems

import io.github.truepadawan.ktlint.problems.tasks.KtLintCheckTask
import io.github.truepadawan.ktlint.problems.tasks.KtLintFormatTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.problems.ProblemGroup

class KtlintProblemsPlugin : Plugin<Project> {
    companion object {
        val PROBLEM_GROUP: ProblemGroup = ProblemGroup.create("ktlintProblems", "KtLint Problems Plugin")
    }

    override fun apply(project: Project) {
        project.tasks.register("ktlintCheck", KtLintCheckTask::class.java) { task ->
            task.description =
                "Run KtLint linter on all kotlin files in the project and report issues via the Problems API"
            task.group = "ktlint problems plugin"
        }
        project.tasks.register("ktlintFormat", KtLintFormatTask::class.java) { task ->
            task.description =
                "Run KtLint formatter on all kotlin files in the project and report issues via the Problems API"
            task.group = "ktlint problems plugin"
        }
    }
}
