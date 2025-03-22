package io.github.truepadawan.ktlint.problems.invoker

import com.pinterest.ktlint.cli.ruleset.core.api.RuleSetProviderV3
import com.pinterest.ktlint.rule.engine.api.Code
import com.pinterest.ktlint.rule.engine.api.KtLintRuleEngine
import com.pinterest.ktlint.rule.engine.api.LintError
import com.pinterest.ktlint.rule.engine.core.api.AutocorrectDecision
import com.pinterest.ktlint.rule.engine.core.api.RuleProvider
import java.io.File
import java.util.ServiceConfigurationError
import java.util.ServiceLoader

class KtLintInvoker(
    private val engine: KtLintRuleEngine,
) {
    companion object Factory {
        fun initialize(): KtLintInvoker {
            val ruleProviders = loadRuleSetsFromClassPath()
            val engine = KtLintRuleEngine(ruleProviders = ruleProviders)
            return KtLintInvoker(engine)
        }

        private fun loadRuleSetsFromClassPath(): Set<RuleProvider> {
            try {
                return ServiceLoader
                    .load(RuleSetProviderV3::class.java)
                    .flatMap {
                        it.getRuleProviders()
                    }.toSet()
            } catch (e: ServiceConfigurationError) {
                println("Error while loading rulesets")
                println(e.printStackTrace())
                return emptySet()
            }
        }
    }

    fun invokeLinter(file: File): LintErrorResult {
        val errors = mutableListOf<LintError>()
        engine.lint(Code.fromFile(file)) { lintError: LintError ->
            errors.add(lintError)
        }
        return LintErrorResult(file, errors)
    }

    fun invokeFormatter(file: File): Pair<String, LintErrorResult> {
        val errors = mutableListOf<LintError>()
        val formattedCode =
            engine.format(Code.fromFile(file)) { lintError ->
                errors.add(lintError)
                if (lintError.canBeAutoCorrected) {
                    AutocorrectDecision.ALLOW_AUTOCORRECT
                } else {
                    AutocorrectDecision.NO_AUTOCORRECT
                }
            }
        return formattedCode to LintErrorResult(file, errors)
    }
}

data class LintErrorResult(
    val file: File,
    val errors: List<LintError>,
)
