class EnglishAlphabet {

    public static StringBuilder createEnglishAlphabet() {
        StringBuilder sb = new StringBuilder();
        for (char i = 'A'; i <= 'Z'; i++) {
            if (sb.length() != 0) {
                sb.append(" ");
            }
            sb.append(i);
        }
        return sb;
    }

    public static void main(String[] args) {
        System.out.println(createEnglishAlphabet());
    }
}