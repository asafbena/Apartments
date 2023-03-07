//package application;
//
//import lombok.Getter;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//public class SimilarNames {
//
//    public static List<NameSimilarity> getSimilarNames(String input, List<NamePair> allNames) {
//        List<NameSimilarity> namesSimilarity = new ArrayList<>();
//        allNames.forEach(originalNamePair -> {
//            int namesDistance = calculateNamesDistance(input, originalNamePair);
//            NameSimilarity nameSimilarity = new NameSimilarity(originalNamePair, namesDistance);
//            namesSimilarity.add(nameSimilarity);
//        });
//
//        Collections.sort(namesSimilarity, new NameSimilarity.NameSimilarityComparator());
//        return namesSimilarity.subList(0, 3);
//
//    }
//
//    private static int calculateNamesDistance(String input, NamePair originalNamePair) {
//        int firstNameDistance = 0;
//        int lastNameDistance = 0;
//        firstNameDistance = calculateNameDistance(input, originalNamePair.firstName);
//        lastNameDistance = calculateNameDistance(input, originalNamePair.lastName);
//        return firstNameDistance <= lastNameDistance ? firstNameDistance : lastNameDistance;
//    }
//
//    private static int calculateNameDistance(String input, String originalName) {
//        int namesDistance = 0;
//        int inputLength = input.length();
//        int originalNameLength = originalName.length();
//        int nameCountIterator = inputLength <= originalNameLength ? inputLength : originalNameLength;
//
//        namesDistance += Math.abs(inputLength - originalNameLength);
//        for (int i = 0; i < nameCountIterator; i++) {
//            if (input.charAt(i) != originalName.charAt(i))
//                namesDistance++;
//        }
//
//        return namesDistance;
//    }
//
//
//    static class NameSimilarity {
//        NamePair namePair;
//        @Getter
//        Integer distance;
//
//        public NameSimilarity(NamePair namePair, int distance) {
//            this.namePair = namePair;
//            this.distance = distance;
//        }
//
//        @Override
//        public String toString() {
//            return "(" + namePair + ")";
//        }
//
//
//        public static class NameSimilarityComparator implements Comparator<NameSimilarity> {
//            @Override
//            public int compare(NameSimilarity o1, NameSimilarity o2) {
//                return (o1.distance).compareTo(o2.distance);
//            }
//        }
//    }
//
//
//}
