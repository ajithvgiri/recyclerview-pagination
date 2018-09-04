# InfinityScroll

Android library for implement pagination (Infinite Scrolling) is used to break down a list of content into equal smaller pieces and load one at time in **RecyclerView**

## Setup

### 1.Provide the gradle dependency
Add it in your root build.gradle at the end of repositories:
```
repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
Add the gradle dependency to your app module build.gradle file:
```
dependencies {
    implementation 'com.github.ajithvgiri:recyclerview-pagination:v1.0'
}
```

### 2. Implementation of InfinityScroll in your project

Initialise these variables as global which is used for pagination
```   
 /**
    * Variables used for Pagination
    */
    private var currentPage = 0
    private val totalPages = 100
    private var loading = false
    private var isLastPage = false
```

Add Scroll Listener for the recyclerview
```
recyclerView.addOnScrollListener(object : RecyclerViewScrollListener(linearLayoutManager) {

            override val isLastPage: Boolean = isLastPage
            override var isLoading: Boolean = loading
            override fun loadNextPage() {
                loading = true
                currentPage += 1
                //API call function
                loadScrollPage()
            }
        })
```
