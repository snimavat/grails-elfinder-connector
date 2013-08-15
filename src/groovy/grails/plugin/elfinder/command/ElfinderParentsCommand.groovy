package grails.plugin.elfinder.command

/**
 * @author Sudhir Nimavat
 */
class ElfinderParentsCommand extends ElfinderBaseCommand {
	@Override
	void execute() {
		String target = params['target']

		if(!target) {
			addError("errOpen")
		} else {
			parents(target)
		}
	}
}
