class Auxili{
    public static void main(String[] args) {
        int input = 0;
        int nb = -1;
        switch (input){
            case 0:
                nb = 1;
                break;
            case 1:
                nb = 0;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + input);
        }
        System.out.println(nb);
    }
}