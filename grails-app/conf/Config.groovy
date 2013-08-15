log4j = {
	appenders {
		console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
	}
	error 'org.codehaus.groovy.grails',
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate'
	debug 'grails.plugin.elfinder'
}

grails.plugin.elfinder.rootDir = "test/integration/resources"
