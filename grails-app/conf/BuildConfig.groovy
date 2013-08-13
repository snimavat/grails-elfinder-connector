grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
	inherits("global") {
	}
	log "warn"
	legacyResolve false
	repositories {
		grailsCentral()
		mavenCentral()
		mavenLocal()
	}
	dependencies {
		test("org.spockframework:spock-grails-support:0.7-groovy-2.0") { export = false }
	}

	plugins {
		build(":tomcat:$grailsVersion", ":release:2.2.1", ":rest-client-builder:1.0.3") { export = false }
		test(":spock:latest.integration") { export = false }
	}
}
