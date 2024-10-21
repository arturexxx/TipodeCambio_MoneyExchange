import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Principal {
    public static void main(String[] args) {
        ConsultaTipoCambio tipodeCambio = new ConsultaTipoCambio();
        Scanner scanner = new Scanner(System.in);
        List<String> historial = new ArrayList<>();

        Map<Integer, String> opcionesMoneda = Map.of(
                1, "USD",
                2, "EUR",
                3, "ARS",
                4, "PEN",
                5, "BRL",
                6, "COP",
                7, "JPY",
                8, "BOB",
                9, "CLP"
        );
        boolean salir = false;

        while (!salir) {
            // Menú principal
            System.out.println("\n---- Menú Principal ----");
            System.out.println("[1] Calcular tipo de cambio");
            System.out.println("[2] Historial de consultas de Cambios");
            System.out.println("[3] Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> {
                    calcularTipoDeCambio(tipodeCambio, scanner, historial);
                    System.out.println("Presione Enter para continuar...");
                    scanner.nextLine();
                    scanner.nextLine();
                }
                case 2 -> {
                    System.out.println("\n---- Historial de Conversión ----");
                    if (historial.isEmpty()) {
                        System.out.println("No hay consultas registradas.");
                    } else {
                        for (String registro : historial) {
                            System.out.println(registro);
                        }
                        System.out.println("Presione Enter para continuar...");
                        scanner.nextLine();
                        scanner.nextLine();
                    }
                }
                case 3 -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }

        System.out.println("Gracias por usar [MoneyExchange].");
    }

    private static void calcularTipoDeCambio(ConsultaTipoCambio tipodeCambio, Scanner scanner, List<String> historial) {
        Map<Integer, String> opcionesMoneda = Map.of(
                1, "USD",
                2, "EUR",
                3, "ARS",
                4, "PEN",
                5, "BRL",
                6, "COP",
                7, "JPY",
                8, "BOB",
                9, "CLP"
        );

        System.out.println("\n---- Selección de opciones de Tipo de Cambio ----");
        System.out.println("[1] Dólar");
        System.out.println("[2] Euro");
        System.out.println("[3] Peso Argentino");
        System.out.println("[4] Sol Peruano");
        System.out.println("[5] Real Brasileño");
        System.out.println("[6] Peso Colombiano");
        System.out.println("[7] Yen Japonés");
        System.out.println("[8] Bolivianos");
        System.out.println("[9] Peso Chileno");
        System.out.println("[10] <-- Volver al menu Anterior");

        System.out.print("Ingrese tipo de moneda [De] origen (número del menú): ");
        int de = scanner.nextInt();
        if (de == 10) {
            System.out.println("Volviendo al menú anterior...");
            return;
        }
        System.out.print("Ingrese tipo de moneda [A] Cambiar (número del menú): ");
        int a = scanner.nextInt();
        if (a == 10) {
            System.out.println("Volviendo al menú anterior...");
            return;
        }
        System.out.print("Ingrese importe a Cambiar: ");
        double importe = scanner.nextDouble();

        if (opcionesMoneda.containsKey(de) && opcionesMoneda.containsKey(a)) {
            String monedaOrigen = opcionesMoneda.get(de);
            String monedaDestino = opcionesMoneda.get(a);

            MoneyChange moneyChangex = tipodeCambio.moneyChange(monedaOrigen);

            if (moneyChangex != null && moneyChangex.getConversion_rates().containsKey(monedaDestino)) {
                double tasaConversion = moneyChangex.getConversion_rates().get(monedaDestino);
                double resultado = importe * tasaConversion;

                // Mostrar el resultado
                System.out.printf("El tipo de cambio de %.2f %s a %s es %.2f%n",
                        importe, monedaOrigen, monedaDestino, resultado);

                // Guardar en el historial
                String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String registro = String.format("[%s] %.2f %s a %s: %.2f",
                        fechaHora, importe, monedaOrigen, monedaDestino, resultado);
                historial.add(registro);
            } else {
                System.out.println("No se pudo obtener la tasa de cambio para " + monedaDestino);
            }
        } else {
            System.out.println("Selección de moneda no válida.");
        }
    }

}



