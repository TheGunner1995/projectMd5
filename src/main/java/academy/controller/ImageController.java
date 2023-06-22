package academy.controller;

import academy.model.Image;
import academy.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
public class ImageController {
    @Autowired
    private IImageService imageService;
    @PutMapping("/updateImg/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Image> updateImg(@PathVariable Long id, @RequestBody Image image){
        Optional<Image> img = imageService.findById(id);
        if (img.isPresent()){
            image.setImgId(id);
            imageService.save(image);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteImg/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Image> deleteImg(@PathVariable Long id){
        Optional<Image> img = imageService.findById(id);
        if (img.isPresent()){
            imageService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/createImg")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Image> createImg(@RequestBody Image image){
        imageService.save(image);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
