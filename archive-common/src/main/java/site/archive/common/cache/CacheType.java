package site.archive.common.cache;

import lombok.Getter;

@Getter
public enum CacheType {

    BANNERS(CacheInfo.BANNERS, CacheInfo.BANNERS_GET, 1),
    ARCHIVE_COMMUNITIES(CacheInfo.ARCHIVE_COMMUNITIES, CacheInfo.ARCHIVE_COMMUNITIES_GET, 200)
    ;

    private final String name;
    private final int expireAfterWrite; // SECONDS TimeUnit
    private final int maximumSize;

    CacheType(String name, int expireAfterWrite, int maximumSize) {
        this.name = name;
        this.expireAfterWrite = expireAfterWrite;
        this.maximumSize = maximumSize;
    }

    public static class CacheInfo {

        public static final String BANNERS = "banners";
        static final int BANNERS_GET = 3_600;   // 1 hour

        public static final String ARCHIVE_COMMUNITIES = "archive-communities";
        static final int ARCHIVE_COMMUNITIES_GET = 30;

        private CacheInfo() {
        }

    }

}
