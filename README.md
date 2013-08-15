grails-elfinder-connector
=========================

Elfinder file browser connector for Grails

elfinder demo : http://elfinder.org/

### How to use.
*Step 1:*
Configure root directory using `grails.plugin.elfinder.rootDir = "PATH_TO_ROOT_DIR"`

*Step 2:*
Now point your elfinder client to index action of *ElfinderConnectorController*

Example:
```
<script>
	$().ready(function() {
		var elf = $('#elfinder').elfinder({
			url : '${g.createLink(controller:'elfinderConnector')}'
		}).elfinder('instance');
	});
</script>
```
