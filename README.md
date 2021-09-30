
# gradle-commit
Gradle plugin which helps to commit &amp; push changes to Git repository

## Purpose

The purpose of this plugin is to commit the changes to the Git automatically after the tools which generate the files
from sources and giving all the time different file names. As a good example, it is a  https://docusaurus.io/.
Whenever you change markdown files, you have to regenerate docs, add new files to Git, commit and push them.
All of that takes time and effort to do it manually, and can be sometimes overseen. 

In order to hide low level details of dealing with Git, the sequence of actions, and moreover to not repeat the code
across the repositories, this plugin has been created.

## Usage

```groovy
plugins {
    id: 'ai.digital.gradle-commit'
}
```

Available parameters

|Name|Type|Default Value|Description|
| :---: | :---: | :---: | :---: |
|gitFileContent|Optional|-A|In case of specified, command `git add 'fileContent'` will be executed.|
|gitMessage|Optional|Blank commit message.|A message to be printed in the Git message.|

## Development

Publish to local maven repository: `./publish_to_local_maven.sh`

Adding the next in your project to use a local version: 

```groovy
buildscript {
    repositories {
        mavenLocal()
    }

    dependencies {
        classpath "ai.digital.gradle.plugins:gradle-commit:VERSION"
    }
}

//...

apply plugin: 'ai.digital.gradle-commit'

//...

```

Run command like this to test it: `./gradlew commitChanges -PgitMessage="my message" -PgitFileContent="docs/*"`

