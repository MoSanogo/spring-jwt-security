package com.mosan.mosan.rest.jwtoken.controllers;


import com.mosan.mosan.rest.jwtoken.controllers.RequestParams.AnimalSearch;
import com.mosan.mosan.rest.jwtoken.dao.domain.Animal;
import com.mosan.mosan.rest.jwtoken.dao.domain.User;
import com.mosan.mosan.rest.jwtoken.dao.repository.IAnimalRepository;
import com.mosan.mosan.rest.jwtoken.dao.repository.IUserRepository;
import com.mosan.mosan.rest.jwtoken.dtos.AnimalCreateDto;
import com.mosan.mosan.rest.jwtoken.dtos.AnimalUpdateDto;
import com.mosan.mosan.rest.jwtoken.exceptions.NotFound;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static com.mosan.mosan.rest.jwtoken.dao.domain.metamodels.Animal_.*;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/animals")
public class PetController {
    @Autowired
    private IAnimalRepository petRepository;
    @Autowired
    private IUserRepository userRepository;
    @PreAuthorize("@permission.decide(#root)")
    @GetMapping("")
    public ResponseEntity<Collection<Animal>> getPetRecords(@Valid @RequestParam(required = false) AnimalSearch animalSearch){
        Collection<Animal> result;
       if(animalSearch==null){
           result=petRepository.findAll(Sort.by(Sort.Direction.ASC,Age));
           return ResponseEntity.status(200).body(result);
       }
       result=petRepository.findAll((root,query,criteriaBuilder)-> criteriaBuilder.and(
                       criteriaBuilder.equal(root.get(Breed),animalSearch.breed()),
                       criteriaBuilder.equal(root.get(Age),animalSearch.Age()),
                       criteriaBuilder.equal(root.get(IsVaccinated),animalSearch.isVaccinated())),
               Sort.by(Sort.Direction.ASC,Age));
       return ResponseEntity.status(OK).body(result);

    }
    @PreAuthorize("hasAuthority('subscription.write')")
    @PostMapping("")
    public ResponseEntity<Animal> createPetRecord(@Valid @RequestBody AnimalCreateDto dto){
            Animal result=petRepository.save(Animal.builder()
                    .breed(dto.breed())
                    .Name(dto.name())
                    .age(dto.age())
                    .isVaccinated(dto.isVaccinate())
                    .build());

        return ResponseEntity.status(CREATED).body(result);

    }

    @PreAuthorize("hasAuthority('subscription.read')")
    @GetMapping("/{animalId}")
    public ResponseEntity<Animal> getPetRecord(@PathVariable UUID animalId) throws NotFound {
       Animal result=petRepository.findById(animalId).orElseThrow(()->new NotFound("could not find any record for animalId: "+ animalId) );
      return ResponseEntity.status(OK).body(result);
    }
    @PreAuthorize("hasAuthority('subscription.write')")
    @PatchMapping("/{animalId}")
    public ResponseEntity<Animal>  updatePetRecord(@PathVariable UUID animalId,@Valid @RequestBody AnimalUpdateDto dto) throws NotFound {
        Animal candidate=petRepository.findById(animalId).orElseThrow(()->new NotFound("could not find any record for animalId: "+ animalId) );
        candidate.setAge(dto.age());
        candidate.setVaccinated(dto.isVaccinate());
        candidate.setName(dto.name());
        candidate.setBreed(dto.breed());
        if(dto.ownerId() !=null) {
            User owner=userRepository.findById(dto.ownerId()).orElseThrow(()->new NotFound("could not find any record for userId: "+dto.ownerId()));
            candidate.setOwner(owner);
        }
        Animal result=petRepository.save(candidate);
      return ResponseEntity.status(OK).body(result);
    }


    @PreAuthorize("hasAuthority('subscription.write')")
    @DeleteMapping("/{animalId}")
    public ResponseEntity<Animal>  deletePetRecord(@PathVariable UUID animalId) throws NotFound {
        Animal candidate= petRepository.findById(animalId).orElseThrow(()->new NotFound("could not find any record for animalId: "+ animalId) );
        petRepository.delete(candidate);
        return ResponseEntity.status(OK).body(candidate);
    }

    @PreAuthorize("hasAuthority('subscription.write')")
    @DeleteMapping("/records")
    public boolean deletePetRecords(@RequestBody Collection<UUID> ids){
       petRepository.deleteAllByIdInBatch(ids);
      return true;
    }
    @PreAuthorize("hasAuthority('subscription.read')")
    @GetMapping("/records")
    public Collection<Animal>getPetRecordsById(@RequestBody Collection<UUID> ids) {
        return petRepository.findAllById(ids);
    }
    @PreAuthorize("hasAuthority('subscription.write')")
    @PostMapping("/records")
    public ResponseEntity<Boolean> createPetRecord(@RequestBody Collection<Animal> dto){
        //We can use a mapper to map AnimalCreateDto to Animal;
        boolean result= petRepository.persistAll(dto);
        return ResponseEntity.status(CREATED).body(true);
    }
}

