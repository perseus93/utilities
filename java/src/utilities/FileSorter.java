package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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

		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cli = parser.parse(options, args);

			File root = new File(cli.getOptionValue('d'));
			if (!root.exists()) {
				System.err.println("Target directory not found.");
				return;
			} else if (!root.isDirectory()) {
				System.err.println("Target directory is not a directory");
				return;
			}

			for (File f : root.listFiles()) {
				if (f.isFile()) {
					try {
						BasicFileAttributes attr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);

						FileTime fTime = attr.creationTime();
						Date d = new Date(fTime.toMillis());

						DateFormat yearFmt = new SimpleDateFormat("yyyy");
						DateFormat monthFmt = new SimpleDateFormat("MM");

						File subDir = new File(root.getAbsolutePath()	+ File.separatorChar + yearFmt.format(d) + File.separatorChar
												+ monthFmt.format(d) + File.separatorChar);

						// Create directories if necessary
						if (!subDir.exists())
							subDir.mkdirs();

						// Move the file
						File destination = new File(subDir.getAbsolutePath() + File.separatorChar + f.getName());
						System.out.println(destination.getAbsolutePath());

						f.renameTo(destination);

					} catch (IOException e) {
						System.err.printf("[ERROR] Unable to read or move file: %s\n", f.getName());
					}
				}
			}

		} catch (ParseException e) {
			// Unable to parse arguments, print stacktrace and abort.
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Defines the CLI options for this utility code.
	 * 
	 */
	public static Options defineOptions() {
		// Define the CLI options
		Options options = new Options();

		Option fileOpt = Option	.builder("d")
								.argName("directory")
								.desc("The directory that is operated upon")
								.required()
								.numberOfArgs(1)
								.longOpt("DIRECTORY")
								.build();
		options.addOption(fileOpt);

		// TODO: Add

		return options;
	}

}
