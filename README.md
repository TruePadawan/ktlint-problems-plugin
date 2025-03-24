# KtLint Problems Plugin
A code sample that shows how Gradle's Problems API can be used to report problems and generate html reports. The project comprises a gradle plugin and a simple application for testing the plugin.

## Tasks
The plugin adds two tasks to the project it is applied to:  

`ktlintCheck` - Runs the KtLint linter on all kotlin files in the project and reports any lint issue(s) found  
`ktlintFormat` - Runs the KtLint formatter on all kotlin files in the project and reports any lint issue(s) it found before formatting
