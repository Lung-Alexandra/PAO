# GalleryApp

## Actions and Queries

1. View all images and videos in the gallery
2. Add an image or video to the gallery
3. Delete an image or video from the gallery
4. Search for an image or video by name
5. Update the name and description of an image or video
6. Add or remove a tag from an image or video
7. View elements by tag
8. Sort images and videos using different criteria
9. Create an empty album
10. Add elements to an album
11. Remove an element from an album
12. Delete an album
13. View album contents
14. Filter images and videos using different criteria

## Object Types

1. `Element`
Represents a general media item, either an image or a video. It includes attributes such as name, description, size, and creation date and time.

2. `Videoclip`
An entity derived from `Element` that includes additional information such as video duration.

3. `Imagine`
An entity derived from `Element` that includes data such as resolution or location.

4. `Fotografie`
A class derived from `Imagine` that stores extra information, such as the camera type and the settings used when the photo was taken.

5. `Eticheta`
Represents a category or theme associated with one or more images or videos. It is used to organize and manage media items based on different criteria.

6. `Album`
Represents a collection of images or videos grouped by specific criteria. Albums can be used to organize media by event, location, or theme.

7. `ServiciuGalerie`
A service class that provides the system operations, such as adding, deleting, searching, and sorting media items, as well as creating and managing tags and albums.

8. `Filtru`
A helper class used to filter images and videos according to criteria such as name, date, size, or other attributes.

9. `ElementComparator`
A class that provides comparison logic for two `Element` objects based on a chosen criterion.
