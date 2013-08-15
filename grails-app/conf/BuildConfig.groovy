grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()
	}

	dependencies {
		test("org.spockframework:spock-grails-support:0.7-groovy-2.0") { export = false }
		compile "commons-io:commons-io:2.4"
	}

	plugins {
		build(":release:2.2.1", ":rest-client-builder:1.0.3") { export = false }
		test(":spock:latest.integration") { export = false }
	}
}
