## Important notes

 - Do not call `addOnPageChangeListener` on the ViewPager you pass to the NavBarLayout. 
   Instead call it on `navBarLayout`:
   ``` java
   navBarLayout.addOnPageChangeListener(new OnPageChangeListener() {...})
   ```
   
- Do not call `setOnClickListener` on NavViews. Instead:
    ``` java
    navBarLayout.setOnNavViewClickListener(new OnNavViewClickListener() {
                @Override
                public void onClick(NavView view, int position, boolean isActive) {
                    // you can get reference to each view either by #view.getId(),
                    // or by checking its position in the navigation bar layout. 
                    // **position starts at 0**
                }
            });
    ```

## Contribution

### Guidelines
 - `return` the instance of the class in public methods for
 easy creation of objects.