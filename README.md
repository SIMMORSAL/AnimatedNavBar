## Important notes

 - Do not call `addOnPageChangeListener` on the ViewPager you pass to the NavBarLayout. 
   Instead call it on `navBarLayout`:\
   ``` java
   navBarLayout.addOnPageChangeListener(new OnPageChangeListener() {...})
   ```

## Contribution

### Guidelines
 - `return` the instance of the class in public methods for
 easy creation of objects.