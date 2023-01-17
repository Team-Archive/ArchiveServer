package site.archive.common.cache

enum class CacheType(val key: String, val expireAfterWrite: Int, val maximumSize: Int) {

    BANNERS(CacheInfo.BANNERS, CacheInfo.BANNERS_GET_EXPIRE_TIME, CacheInfo.BANNERS_CACHE_SIZE),
    ARCHIVE_COMMUNITIES(CacheInfo.ARCHIVE_COMMUNITIES, CacheInfo.ARCHIVE_COMMUNITIES_GET_EXPIRE_TIME, CacheInfo.ARCHIVE_COMMUNITIES_CACHE_SIZE)
    ;

    class CacheInfo {
        companion object {
            const val BANNERS: String = "banners"
            const val BANNERS_GET_EXPIRE_TIME: Int = 3_600
            const val BANNERS_CACHE_SIZE: Int = 1

            const val ARCHIVE_COMMUNITIES: String = "archive-communities"
            const val ARCHIVE_COMMUNITIES_GET_EXPIRE_TIME: Int = 30
            const val ARCHIVE_COMMUNITIES_CACHE_SIZE: Int = 200
        }
    }

}