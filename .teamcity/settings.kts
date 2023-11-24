import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

version = "2023.11"

project {
    vcsRoot(Repo3)
    vcsRoot(Repo3, "+:. => repo3")
    buildType(Build1)
}

object Build1 : BuildType({
    name = "build1"

    vcs {
        root(DslContext.settingsRoot)
        root(Repo3, "+:. => repo3")
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """
                echo "##teamcity[testStarted name='MyTest.test2']"
                echo "##teamcity[testFailed type='comparisonFailure' name='MyTest.test2' message='failure message' details='message and stack trace' expected='expected value' actual='actual value']"
                echo "##teamcity[testFinished name='MyTest.test2']"
            """.trimIndent()
        }
    }
})

object Repo3 : GitVcsRoot({
    name = "https://github.com/ChubatovaTiger/repo3"
    url = "https://github.com/ChubatovaTiger/repo3"
    branch = "refs/heads/main"
    authMethod = password {
        userName = "ChubatovaTiger"
        password = "credentialsJSON:a34cf740-b3bf-4a18-b387-5b54dc880bc0"
    }
})
