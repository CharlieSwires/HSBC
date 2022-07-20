package restful;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoBean;
import com.mongodb.MongoBeanRepository;

@Service
public class UserService {

    @Autowired
    private MongoBeanRepository beanRepository;

    public synchronized Boolean save(RequestBean input) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null");
        }
        delete(input.getFirstname(), input.getSurname());
        MongoBean mb  = new MongoBean();
        mb.setFirstname(input.getFirstname());
        mb.setSurname(input.getSurname());
        mb.setTitle(input.getTitle());
        mb.setDOB(input.getDOB());
        mb.setJobTitle(input.getJobTitle());
        mb.setCreateStamp(new Date());
        beanRepository.save(mb);
        return true;
    }

    public synchronized Boolean delete(String firstName, String lastName) {
        MongoBean mb = beanRepository.findByFirstnameSurname(firstName, lastName);
        if (mb == null) return false;
        beanRepository.deleteById(mb.getId());
        return true;
    }
    public synchronized Boolean deleteById(String id) {
        beanRepository.deleteById(id);
        return true;
    }

    public ResponseBean get(String firstName, String lastName) {
        MongoBean mb  = beanRepository.findByFirstnameSurname(firstName,lastName);
        if (mb == null) return null;
        ResponseBean rb = new ResponseBean();
        rb.setFirstname(mb.getFirstname());
        rb.setSurname(mb.getSurname());
        rb.setTitle(mb.getTitle());
        rb.setCreateStamp(mb.getCreateStamp().toString());
        rb.setDOB(LocalDate.parse(mb.getDOB()));
        rb.setJobTitle(mb.getJobTitle());
        rb.setId(mb.getId());
        return rb;
    }

    public List<ResponseBean> getAll(String lastName) {
        List<MongoBean> mbs = beanRepository.findBySurnameOrderByFirstname(lastName);
        if (mbs == null) return null;
        List<ResponseBean> rbs = new ArrayList<ResponseBean>();
        for (MongoBean mb: mbs) {
            ResponseBean rb = new ResponseBean();
            rb.setFirstname(mb.getFirstname());
            rb.setSurname(mb.getSurname());
            rb.setTitle(mb.getTitle());
            rb.setCreateStamp(mb.getCreateStamp().toString());
            rb.setDOB(LocalDate.parse(mb.getDOB()));
            rb.setJobTitle(mb.getJobTitle());
            rb.setId(mb.getId());
            rbs.add(rb);
        }
        return rbs;
    }
    public List<ResponseBean> getAllFirstname(String firstname) {
        List<MongoBean> mbs = beanRepository.findByFirstnameOrderBySurname(firstname);
        if (mbs == null) return null;
        List<ResponseBean> rbs = new ArrayList<ResponseBean>();
        for (MongoBean mb: mbs) {
            ResponseBean rb = new ResponseBean();
            rb.setFirstname(mb.getFirstname());
            rb.setSurname(mb.getSurname());
            rb.setTitle(mb.getTitle());
            rb.setCreateStamp(mb.getCreateStamp().toString());
            rb.setDOB(LocalDate.parse(mb.getDOB()));
            rb.setJobTitle(mb.getJobTitle());
            rb.setId(mb.getId());
            rbs.add(rb);
        }
        return rbs;
    }
    public synchronized void getAllCSV() throws IOException {
        List<MongoBean> mbs = beanRepository.findAll();
        Collections.sort(mbs, new Comparator<MongoBean>() {

            @Override
            public int compare(MongoBean mb1, MongoBean mb2) {
                if (mb1 == null || mb2 == null) return 0;
                String dmb1 = mb1.getSurname() != null ? mb1.getSurname() : "";
                String dmb2 = mb2.getSurname() != null ? mb2.getSurname() : "";
                int comp = dmb1.compareToIgnoreCase(dmb2);
                dmb1 = mb1.getFirstname() != null ? mb1.getFirstname() : "";
                dmb2 = mb2.getFirstname() != null ? mb2.getFirstname() : "";
                if (comp == 0) comp = dmb1.compareToIgnoreCase(dmb2);
                return comp;
            }
        });

        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("hsbc.csv"));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("id","Title","Firstname","Surname","DOB","JobTitle","CreateStamp"));
                ) {
            for(MongoBean mb: mbs) {
                csvPrinter.printRecord(mb.getId(),
                        mb.getTitle(),
                        mb.getFirstname(),
                        mb.getSurname(),
                        mb.getDOB(),
                        mb.getJobTitle(),
                        mb.getCreateStamp());

            }

            csvPrinter.flush();            
        }    
    }
    public synchronized void loadFromCSV() throws IOException {
        beanRepository.deleteAll();
        try (
                Reader reader = Files.newBufferedReader(Paths.get("addressbook.csv"));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withHeader("id","Title","Firstname","Surname","DOB","JobTitle","CreateStamp")
                        .withIgnoreHeaderCase()
                        .withTrim());
                ) {
            int line = 0;
            for (CSVRecord csvRecord : csvParser) {
                if (line++ == 0) continue;
                MongoBean mb = new MongoBean();
                // Accessing values by the names assigned to each column
                mb.setId(csvRecord.get("id"));
                mb.setTitle(csvRecord.get("Title"));
                mb.setFirstname(csvRecord.get("Firstname"));
                mb.setSurname(csvRecord.get("Surname"));
                mb.setDOB(csvRecord.get("DOB"));
                mb.setJobTitle(csvRecord.get("JobTitle"));
                mb.setCreateStamp(new Date(csvRecord.get("CreateStamp")));
               
                beanRepository.save(mb);

            }
        }
    }

    public List<ResponseBean> getAll() {
        List<MongoBean> mbs = beanRepository.findAll();

        Collections.sort(mbs, new Comparator<MongoBean>() {

            @Override
            public int compare(MongoBean mb1, MongoBean mb2) {
                if (mb1 == null || mb2 == null) return 0;
                String dmb1 = mb1.getSurname() != null ? mb1.getSurname() : "";
                String dmb2 = mb2.getSurname() != null ? mb2.getSurname() : "";
                int comp = dmb1.compareToIgnoreCase(dmb2);
                dmb1 = mb1.getFirstname() != null ? mb1.getFirstname() : "";
                dmb2 = mb2.getFirstname() != null ? mb2.getFirstname() : "";
                if (comp == 0) comp = dmb1.compareToIgnoreCase(dmb2);
                return comp;
            }
        });

        List<ResponseBean> rbs = new ArrayList<ResponseBean>();
        if (mbs == null || mbs.size() == 0) return rbs;
        for (MongoBean mb: mbs) {
            ResponseBean rb = new ResponseBean();
            rb.setFirstname(mb.getFirstname());
            rb.setSurname(mb.getSurname());
            rb.setTitle(mb.getTitle());
            rb.setCreateStamp(mb.getCreateStamp().toString());
            rb.setDOB(LocalDate.parse(mb.getDOB()));
            rb.setJobTitle(mb.getJobTitle());
            rb.setId(mb.getId());
            rbs.add(rb);
        }
        return rbs;
    }

    public ResponseBean getId(String id) {
        Optional<MongoBean> mb  = beanRepository.findById(id);
        if (mb.isEmpty()) return null;
        ResponseBean rb = new ResponseBean();
        rb.setFirstname(mb.get().getFirstname());
        rb.setSurname(mb.get().getSurname());
        rb.setTitle(mb.get().getTitle());
        rb.setCreateStamp(mb.get().getCreateStamp().toString());
        rb.setDOB(LocalDate.parse(mb.get().getDOB()));
        rb.setJobTitle(mb.get().getJobTitle());
        rb.setId(mb.get().getId());
        return rb;
    }



}
