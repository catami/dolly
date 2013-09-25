dolly
=====

Image similarity search built on top of Lire - http://www.semanticmetadata.net/lire/

Dolly has been designed to serve two purposes:
 - Harvest and index all images contained within a given directory
 - Provide a JSON API for Lire's image similarity search capabilities

Use of dolly requires some understanding of how Lire works, and how the image index is being built. We suggest you head over there are read their documentation first - http://www.semanticmetadata.net/wiki/doku.php?id=start

##Installation

    git pull https://github.com/catami/dolly.git
    cd dolly
    mvn clean:clean
    mvn compile war:war
    cp target/dolly.war {your tomcat webapps directory}

Prerequisite: Tomcat, Java7, maven2, git

##Configuration

Specify the directory you want to index, and specify a time constraint on how often you'd like the index to be run in the dolly/src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml file. 

    <bean id="harvester" class="org.catami.dolly.ScheduledDirectoryHarvester">
        <constructor-arg name="directoryToWatch" value="{your absolute directory path here}"/>
    </bean>

    <task:scheduled-tasks>
        <task:scheduled ref="harvester" method="harvestDirectory" fixed-rate="10000"/>
    </task:scheduled-tasks>

##Interacting with the API

    http://host:port/dolly/search
    
    Accepts POST requests only.
    
    Accepts the following parameters: 
     - imagePath = the absolute path to the image which you would like to find similar images for. This path must be in the index, and absolute.
     - imageComparisonList = list of images that exist in the index you'd like to compare against. We designed it this way because we are only interested in comparing defined image subsets.  
     - limit = the number of images to return. Images are ranked by their similarity, so you'll get the closest ones first. 
     - similarityGreater = a number between 0-1. Value closer to 0 means images are more distant. Value closer to 1 means images are closer. A value of 0.8 for example would obtain all images which are greater than 80% similar.
     - featureType = this is the feature extraction method. Currently support 'cedd' or 'gabor'. 
     
    Example request might be:
     imagePath=/var/images/image1.jpg
     imageComparisonList=[/var/images/image2.jpg,/var/images/image3.jpg,/var/images/image41.jpg]
     limit=2
     similarityGreater=0.7
     featureType=cedd
     





