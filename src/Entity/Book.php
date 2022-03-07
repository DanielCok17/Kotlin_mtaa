<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Serializer\Annotation\MaxDepth;

use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\Console\Formatter\OutputFormatterStyle;
/**
 * @ORM\Entity(repositoryClass="App\Repository\BookRepository")
 * @ORM\HasLifecycleCallbacks()
 */
class Book
{
    const SERVER_PATH_TO_IMAGE_FOLDER = '%kernel.project_dir%/public/files';

    /**
     * @Groups("book")
     * @ORM\Id()
     * @ORM\GeneratedValue()
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * Groups("book")
     * @ORM\ManyToOne(targetEntity="App\Entity\Author")
     * @ORM\JoinColumn(nullable=false)
     */
    private $author_id;

    /**
     * Groups("book")
     * @ORM\Column(type="string", length=255)
     */
    private $name;

    /**
     * Groups("book")
     * @ORM\Column(type="string", length=255, nullable=true)
     */

    private $description;


    /**
     * Groups("book")
     * @ORM\Column(type="string", nullable=false)
     */
    private $image;


    /**
     * Groups("book")
     * @ORM\Column(type="datetime", nullable=true)
     */
    private $published_at;

    /**
     * Groups("book")
     * @ORM\Column(type="datetime")
     */
    private $created_at;

    /**
     * Groups("book")
     * @ORM\Column(type="datetime")
     */
    private $updated_at;

    /**
     * Groups("book")
     * @ORM\Column(type="boolean")
     */
    private $is_deleted;


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getAuthorId(): ?Author
    {
        return $this->author_id;
    }

    public function setAuthorId(?Author $author_id): self
    {
        $this->author_id = $author_id;

        return $this;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(string $name): self
    {
        $this->name = $name;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $description): self
    {
        $this->description = $description;

        return $this;
    }

//
    public function getImage(): ?string
    {

        //return File(base64_decode($this->image));
//        return File($this->image);
        return is_resource($this->image) ? stream_get_contents($this->image) : $this->image;
    }


    public function setImage(string $image)
    {
        $this->image =$image;
       // $this->image =base64_encode( $image);
        return $this->image;
    }


//    public function getImage(?UploadedFile $image = null):void
//    {
//        $this->image = $image;
//        //return File(base64_decode($this->image));
//        //return File($this->image);
//        //return is_resource($this->image) ? stream_get_contents($this->image) : $this->image;
//    }
//    public function upload(): void
//    {
//        // the file property can be empty if the field is not required
//        if (null === $this->getFile()) {
//            return;
//        }
//
//        // we use the original file name here but you should
//        // sanitize it at least to avoid any security issues
//
//        // move takes the target directory and target filename as params
//        $this->getFile()->move(
//            self::SERVER_PATH_TO_IMAGE_FOLDER,
//            $this->getFile()->getClientOriginalName()
//        );
//    }
//
//    public function setImage(File $image)
//    {
//        $this->image =$image;
//        $this->image =base64_encode( $image);
//        return $this->image;
//    }


    public function getPublishedAt(): ?\DateTimeInterface
    {
        return $this->published_at;
    }

    public function setPublishedAt(?\DateTimeInterface $published_at): self
    {
        $this->published_at = $published_at;

        return $this;
    }

    public function getCreatedAt(): ?\DateTimeInterface
    {
        return $this->created_at;
    }

    /**
     * @ORM\PrePersist()
     */
    public function setCreatedAt(): self
    {
        $this->created_at = $this->updated_at = new \DateTime();
        $this->setIsDeleted(0);
        return $this;
    }

    public function getUpdatedAt()
    {
        return $this->updated_at;
    }

    /**
     * @ORM\PreUpdate()
     */
    public function setUpdatedAt(): self
    {
        $this->updated_at = new \DateTime();
        return $this;
    }

    public function getIsDeleted(): ?bool
    {
        return $this->is_deleted;
    }

    public function setIsDeleted(bool $is_deleted): self
    {
        $this->is_deleted = $is_deleted;

        return $this;
    }

}