package se.callista.springboot.rest.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.callista.springboot.rest.domain.BedJPA;
import se.callista.springboot.rest.domain.BedRepository;
import se.callista.springboot.rest.domain.CareUnitJPA;
import se.callista.springboot.rest.domain.CareUnitRepository;
import se.callista.springboot.rest.domain.HospitalJPA;
import se.callista.springboot.rest.domain.HospitalRepository;

import javax.annotation.PostConstruct;
import java.util.Random;

@Service
@Profile("example")
public class DatabaseLoader {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private CareUnitRepository careUnitRepository;

    @Autowired
    private BedRepository bedRepository;

    @PostConstruct
    public void initDatabase() throws Exception {
        // Create 5 hospitals to use more regularly
        HospitalJPA hospital1 = hospitalRepository.save(new HospitalJPA( "Sahlgrenska", "Göteborg"));
        HospitalJPA hospital2 = hospitalRepository.save(new HospitalJPA("Östra sjukhuset","Göteborg"));
        HospitalJPA hospital3 = hospitalRepository.save(new HospitalJPA("Södra Älvsborgs sjukhus","Borås"));
        HospitalJPA hospital4 = hospitalRepository.save(new HospitalJPA("Skaraborgs sjukhus","Falköping"));
        HospitalJPA hospital5 = hospitalRepository.save(new HospitalJPA("Uddevall sjukhus","Uddevalla"));

        // Create care units for hospitals
        CareUnitJPA cu1 = createCareunit(hospital1, "Avdelning 10" ,"11111", "Avdelning10.su@vgregion.se" );
        CareUnitJPA cu2 = createCareunit(hospital1, "Avdelning 20" ,"22222", "Avdelning20.su@vgregion.se" );
        CareUnitJPA cu3 = createCareunit(hospital1, "Avdelning 30" ,"33333", "Avdelning30.su@vgregion.se");

        BedJPA bed1_1 = createBed(cu1, "Bed_10_2_1", "2");
        bed1_1.setState(BedJPA.State.OCCUPIED);
        bedRepository.save(bed1_1);

        BedJPA bed1_2 = createBed(cu1, "Bed_10_2_2", "2");
        bed1_2.setState(BedJPA.State.OCCUPIED);
        bedRepository.save(bed1_2);

        BedJPA bed1_3 = createBed(cu1, "Bed_10_4_1", "4");
        bed1_3.setState(BedJPA.State.OCCUPIED);
        bedRepository.save(bed1_3);

        BedJPA bed1_4 = createBed(cu1, "Bed_10_4_2", "4");
        bed1_4.setState(BedJPA.State.OCCUPIED);
        bedRepository.save(bed1_4);

        BedJPA bed2_1 = createBed(cu2, "Bed_20_1_1", "1");
        bed2_1.setState(BedJPA.State.OCCUPIED);
        bedRepository.save(bed2_1);

        BedJPA bed2_2 = createBed(cu2, "Bed_20_1_2", "1");
        bed2_2.setState(BedJPA.State.RESERVED);
        bedRepository.save(bed2_2);

        BedJPA bed2_3 = createBed(cu2, "Bed_20_1_3", "1");
        bed2_3.setState(BedJPA.State.FREE);
        bedRepository.save(bed2_3);

        BedJPA bed3_1 = createBed(cu3, "Bed_30_1_1", "IVA-1");
        bed3_1.setState(BedJPA.State.OCCUPIED);
        bedRepository.save(bed3_1);

        BedJPA bed3_2 = createBed(cu3, "Bed_30_2_1", "IVA-2");
        bed3_2.setState(BedJPA.State.RESERVED);
        bedRepository.save(bed3_2);

        // Create careunits for the rest of the hospitals 2 - 5
        createManyCareunits(hospital2, 10);
        createManyCareunits(hospital3, 15);
        createManyCareunits(hospital4, 5);
        createManyCareunits(hospital5, 25);
    }

    private CareUnitJPA createCareunit(HospitalJPA hospital, String name, String phonenumber, String email) {
        CareUnitJPA careunit = new CareUnitJPA();
        careunit.setName(name);
        careunit.setPhoneNumber(phonenumber);
        careunit.setEmail(email);
        careunit.setHospital(hospital);
        return careUnitRepository.save(careunit);
    }

    private void createManyCareunits(HospitalJPA hospital, int numOfCareUnits) {
        for (int j = 0; j < numOfCareUnits ; j++) {
            String name = hospital.getName() + " Avdelning " + j+ 10;
            String phone = "0123-123" + j/10 + "" + j%10;
            String email = name.replaceAll(" ", "") + "@vgregion.se";
            CareUnitJPA careUnitX = careUnitRepository.save(createCareunit(hospital, name , phone, email));

            for (int k = 0; k < 10 ; k++) {
                String bedName = "Bed_" + j+10 + "_" + k/3 + "_" + k%3;
                String room = "Rum" + k/3;
                BedJPA bedX = createBed(careUnitX, bedName, room);
                bedRepository.save(bedX);
            }
        }
    }

    private BedJPA createBed(CareUnitJPA careunit, String name, String room) {
        BedJPA bedX = new BedJPA();
        bedX.setName(name);
        bedX.setRoom(room);
        bedX.setState(getRandomState());
        bedX.setCareunit(careunit);
        BedJPA savedBedX = bedRepository.save(bedX);
        return savedBedX;
    }

    private BedJPA.State getRandomState() {
        Random rnd = new Random();
        int number = rnd.nextInt(10);
        if (number < 7) {
            return BedJPA.State.OCCUPIED;
        } else if (number < 9) {
            return BedJPA.State.FREE;
        } else {
            return BedJPA.State.RESERVED;
        }
    }
}