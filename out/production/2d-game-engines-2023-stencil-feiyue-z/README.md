# WIZ I Handin README
#### Fill out this README before turning in this project. Make sure to fill this out again for each assignment!
---
### Banner ID: B01912235
---
### Already uploaded demo onto Slack: Yes
---
## Primary Requirements:
| Requirement                                                                                                                                                                                                       | Location in code or steps to view in game                    |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------|
| Your handin must meet all global requirements.                                                                                                                                                                    | ``` NA ```                                                   |
| Your engine must correctly implement saving and loading through serialization.                                                                                                                                    | ``` Saving and loading happen in `nin/game/NinGameWorld` ``` |
| Your engine must correctly support raycasting for polygons and AABs.                                                                                                                                              | ``` See `engine.ray` and `engine.shape` ```                  |
| You must complete the debugger to demonstrate correct raycasting for polygons and AABs.                                                                                                                           | ``` NA ```                                                   |
| Your player must be able to fire projectiles.                                                                                                                                                                     | ``` Press key P to fire projectiles ```                      |
| Your game must be loaded from a file. For this requirement, you can save your game using any file type, formatted as you please. You must provide at least one file that we can load in your game successfully.   | ``` NA ```                                                   |
| You must be able to save your game state, restart the game, and then load that game state.                                                                                                                        | ``` See `nin/game/GameScreen` ```                            |
| The player must always be in view.                                                                                                                                                                                | ``` See `engine.component.center` ```                        |
| It must be possible to start a new game without restarting the program.                                                                                                                                           | ``` NA ```                                                   |



## Secondary Requirements:
| Requirement                                                                                                                                                                | Location in code or steps to view in game |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------|
| Your engine must meet all primary engine requirements.                                                                                                                     | ``` NA ```                                |
| Your engine must correctly support raycasting for circles.                                                                                                                 | ``` NA ```                                |
| You must complete the debugger to demonstrate correct raycasting for circles.                                                                                              | ``` NA ```                                |
| Your game must meet all primary game requirements.                                                                                                                         | ``` NA ```                                |
| There must be a polished UI for saving and loading.                                                                                                                        | ``` NA ```                                |
| Save files must be written in XML format. This will help organize your saves, and also java has code for parsing these files.                                              | ``` See `nin/save.xml` ```                |
| The player must be able to fire projectiles that travel instantly using raycasting. Projectiles must apply an impulse to whatever they hit in the direction of that ray.   | ``` See `nin/object/RayObject` ```        |
| Your game must meet at least two of the extra game requirements.                                                                                                           | ``` NA ```                                |


## Extras:
| Requirement                                                                                                 | Location in code or steps to view in game                        |
|-------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------|
| Make a destructible environment, such as breakable blocks.                                                  | ``` Fire projectiles on blocks, if it get hits it will break ``` |
| The player and enemies are drawn with sprites (and animations when appropriate) instead of vector graphics. | ``` All GameObjects use sprites  ```                             |                                                          |

--------------------------------------------------------------

Instructions on how to run

Known bugs:

Hours spent on assignment: 20 hours
