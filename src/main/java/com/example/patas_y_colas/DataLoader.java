package com.example.patas_y_colas;

import com.example.patas_y_colas.model.*;
import com.example.patas_y_colas.repository.*;

import net.datafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.List;

@Profile("test")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PerroRepository perroRepository;
    @Autowired
    private VacunaRepository vacunaRepository;
    @Autowired
    private VacunacionRepository vacunacionRepository;
    @Autowired
    private AlimentacionRepository alimentacionRepository;
    @Autowired
    private DietaRepository dietaRepository;
    @Autowired
    private HistorialMedicoRepository historialMedicoRepository;
    @Autowired
    private PesoRepository pesoRepository;
    @Autowired
    private TipoComidaRepository tipoComidaRepository;
    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Random());
        Random random = new Random();

        List<Usuario> usuarios = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Usuario usuario = new Usuario();
            String rut = faker.number().digits(8) + "-" + faker.number().digit();
            usuario.setRut(rut);
            usuario.setNombre(faker.name().fullName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setContrasena(faker.internet().password(8, 16));
            usuario.setRol(faker.options().option("usuario", "admin", "veterinario"));
            usuarioRepository.save(usuario);
            usuarios.add(usuario);
        }

        List<Vacuna> vacunas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Vacuna vacuna = new Vacuna();
            vacuna.setIdVacuna(UUID.randomUUID().toString());
            vacuna.setNombre("Vacuna " + faker.expression("#{medical.medicine_name}"));
            vacuna.setDescripcion(faker.lorem().sentence());
            vacuna.setObligatoria(faker.bool().bool());
            vacunas.add(vacunaRepository.save(vacuna));
        }

        for (Usuario usuario : usuarios) {
            int cantidadPerros = faker.number().numberBetween(1, 3);

            for (int j = 0; j < cantidadPerros; j++) {
                Perro perro = new Perro();
                perro.setChipId(faker.idNumber().valid());
                perro.setNombre(faker.dog().name());
                perro.setRaza(faker.dog().breed());
                Date fechaNacimientoDate = faker.date().birthday(1, 12);
                LocalDate fechaNacimiento = fechaNacimientoDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
                perro.setFechaNacimiento(fechaNacimiento);
                perro.setGenero(faker.options().option("Macho", "Hembra"));
                perro.setUsuario(usuario);

                perroRepository.save(perro);

                int cantidadVacunaciones = faker.number().numberBetween(1, 4);
                for (int k = 0; k < cantidadVacunaciones; k++) {
                    Vacuna vacunaAleatoria = vacunas.get(faker.random().nextInt(vacunas.size()));

                    Vacunacion vacunacion = new Vacunacion();
                    vacunacion.setIdVacunacion(UUID.randomUUID().toString());
                    Date fechaAplicacionDate = faker.date().past(365, TimeUnit.DAYS);
                    LocalDate fechaAplicacion = fechaAplicacionDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                    vacunacion.setFechaAplicacion(fechaAplicacion);
                    vacunacion.setFechaProxima(fechaAplicacion.plusMonths(faker.number().numberBetween(3, 12)));
                    vacunacion.setPerro(perro);
                    vacunacion.setVacuna(vacunaAleatoria);
                    vacunacionRepository.save(vacunacion);
                }

                int cantidadAlimentaciones = faker.number().numberBetween(1, 3);
                for (int a = 0; a < cantidadAlimentaciones; a++) {
                    Alimentacion alimentacion = new Alimentacion();
                    alimentacion.setPorcion(faker.options().option("100g", "150g", "200g", "1 taza", "1/2 taza"));
                    alimentacion.setHorario(faker.date().future(1, TimeUnit.DAYS));
                    alimentacion.setPerro(perro);
                    alimentacionRepository.save(alimentacion);
                }

                int cantidadDietas = faker.number().numberBetween(1, 2);
                for (int d = 0; d < cantidadDietas; d++) {
                    Dieta dieta = new Dieta();
                    dieta.setDescripcion(faker.lorem().sentence(10));
                    Date inicioDate = faker.date().past(90, TimeUnit.DAYS);
                    LocalDate inicio = inicioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    dieta.setFechaInicio(inicio);
                    dieta.setFechaTermino(inicio.plusDays(faker.number().numberBetween(10, 60)));
                    dieta.setPerro(perro);
                    dietaRepository.save(dieta);
                }

                int cantidadTipos = faker.number().numberBetween(1, 3);
                for (int t = 0; t < cantidadTipos; t++) {
                    TipoComida tipo = new TipoComida();
                    tipo.setNombre(faker.options().option("Balanceado", "Húmeda", "Barf", "Casera", "Medicada"));
                    tipo.setPerro(perro);
                    tipoComidaRepository.save(tipo);
                }

                HistorialMedico historial = new HistorialMedico();
                historial.setConsultas(faker.expression("#{medical.symptoms}"));
                historial.setDiagnosticos(faker.lorem().words(faker.number().numberBetween(1, 4)));
                List<Float> historialPesos = new ArrayList<>();
                for (int p = 0; p < 3; p++) {
                    historialPesos.add(5 + random.nextFloat() * 15);
                }
                historial.setPesos(historialPesos);
                historial.setFechaConsulta(faker.date().past(60, TimeUnit.DAYS));
                historial.setPerro(perro);
                historialMedicoRepository.save(historial);

                int cantidadPesos = faker.number().numberBetween(2, 4);
                for (int i = 0; i < cantidadPesos; i++) {
                    Peso peso = new Peso();
                    peso.setFecha(faker.date().past(180, TimeUnit.DAYS));
                    peso.setPesoKg(5 + random.nextFloat() * 20);
                    peso.setPerro(perro);
                    pesoRepository.save(peso);
                }

                int cantidadTratamientos = faker.number().numberBetween(1, 3);
                for (int i = 0; i < cantidadTratamientos; i++) {
                    Tratamiento tratamiento = new Tratamiento();
                    tratamiento.setDiagnostico(faker.expression("#{medical.disease_name}"));
                    Date inicio = faker.date().past(180, TimeUnit.DAYS);
                    tratamiento.setFechaInicio(inicio);
                    tratamiento.setFechaTermino(faker.date().future(60, TimeUnit.DAYS, inicio));
                    tratamiento.setPerro(perro);
                    tratamientoRepository.save(tratamiento);
                }

            }
        }
    }
}
