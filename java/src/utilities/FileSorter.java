package utilities;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * A utility used for sorting files in a folder.
 * Example use case, a picture forlder in the cloud with unsorted pictures to be divided over sub-folders with year and
 * month indicatons.
 * Functionality may change and/or be extended in future versions
 * 
 * @author Pieter Schaap - P_je@hotmail.com
 *
 * @version 0.0.1
 */
public class FileSorter {

	public static void main(String[] args) {
		Options options = defineOptions();

	}

	/**
	 * Defines the CLI options for this utility code.
	 * 
	 */
	public static Options defineOptions() {
		// Define the CLI options
		Options options = new Options();

		Option subDirOpt = Option	.builder()
									.argName("Sub-directory format")
									.desc("Defines the format to be used for the subdirectories, e.g.: yyyy/mm will result in subdirectories such as 2017/01 for pictures made in january 2017.")
									.hasArgs()
									.optionalArg(true)
									.longOpt("SUB_DIR_FORMAT")
									.valueSeparator('/')
									.build();
		options.addOption(subDirOpt);

		return options;
	}
}
