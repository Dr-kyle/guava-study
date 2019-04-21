package com.nesc.utilities;


import java.util.Calendar;
import java.util.Objects;

public class ObjectTest {
    static class Guava {
        private final String manuFacturer;
        private final String version;
        private final Calendar releaseData;

        public Guava(String manuFacturer, String version, Calendar releaseData) {
            this.manuFacturer = manuFacturer;
            this.version = version;
            this.releaseData = releaseData;
        }

//        @Override
//        public String toString() {
//            return MoreObjects.toStringHelper(this).omitNullValues()
//                    .add("manuFacturer", this.manuFacturer)
//                    .add("version", this.version)
//                    .add("releaseData", this.releaseData).toString();
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hashCode(manuFacturer, version, releaseData);
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj) {
//                return true;
//            }
//            if (obj == null || getClass() != obj.getClass()) return false;
//
//            Guava guava = (Guava) obj;
//            return Objects.equal(this.manuFacturer, guava.manuFacturer)
//                    && Objects.equal(this.version, guava.version)
//                    && Objects.equal(this.releaseData, guava.releaseData);
//        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Guava guava = (Guava) o;
            return Objects.equals(manuFacturer, guava.manuFacturer) &&
                    Objects.equals(version, guava.version) &&
                    Objects.equals(releaseData, guava.releaseData);
        }

        @Override
        public int hashCode() {
            return Objects.hash(manuFacturer, version, releaseData);
        }

        @Override
        public String toString() {
            return "Guava{" +
                    "manuFacturer='" + manuFacturer + '\'' +
                    ", version='" + version + '\'' +
                    ", releaseData=" + releaseData +
                    '}';
        }
    }
}
