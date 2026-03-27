import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2025.11"

project {

    vcsRoot(HttpsGithubComMazaichExampleTeamcityGitRefsHeadsMaster)

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    artifactRules = "target/*.jar => artifacts"

    params {
        param("branch", "%teamcity.build.branch%")
    }

    vcs {
        root(HttpsGithubComMazaichExampleTeamcityGitRefsHeadsMaster)
    }

    steps {
        script {
            name = "Conditional build"
            id = "simpleRunner"
            scriptContent = """
                #!/bin/bash
                BRANCH=%teamcity.build.branch%
                if [[ "${'$'}BRANCH" == "master" ]] || [[ "${'$'}BRANCH" == "main" ]]; then
                    echo "Running mvn clean deploy with Nexus settings"
                    /usr/bin/mvn clean deploy -s ./settings.xml
                else
                    echo "Running mvn clean test"
                    /usr/bin/mvn clean test
                fi
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object HttpsGithubComMazaichExampleTeamcityGitRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/Mazaich/example-teamcity.git#refs/heads/master"
    url = "https://github.com/Mazaich/example-teamcity.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
})
