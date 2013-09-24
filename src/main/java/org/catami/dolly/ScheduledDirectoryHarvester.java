package org.catami.dolly;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by mat on 24/09/13.
 *
 * This class monitors a given directory, compares the file contents with the lire index,
 * and then indexes non indexed images.
 *
 */

public class ScheduledDirectoryHarvester {

    private static Logger logger = Logger.getLogger("ScheduledDirectoryHarvester");
    private String directoryToWatch = "/Users/mat/Dev/catami-data/importedimages";

    public ScheduledDirectoryHarvester() {
        logger.info("Loaded default constructor ScheduledDirectoryHarvester");
    }

    public ScheduledDirectoryHarvester(String directoryToWatch) {
        logger.info("Loaded constructor ScheduledDirectoryHarvester with directory: " + directoryToWatch);
        this.directoryToWatch = directoryToWatch;
    }

    //@Scheduled(fixedDelay=10000)
    public void harvestDirectory() {
        //run the indexer - we don't need to worry about much, because Lire does not overwrite the index
        //it just appends new entries
        try {
            new LireIndexer().indexDirectory(this.directoryToWatch);
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
