<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints\Date;
use Symfony\Component\Validator\Constraints\DateTime;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Serializer\Annotation\MaxDepth;
/**
 * @ORM\Entity(repositoryClass="App\Repository\BookItemRepository")
 * @ORM\HasLifecycleCallbacks()
 */
class BookItem
{
    /**
     * @Groups("Bookitem")
     * @ORM\Id()
     * @ORM\GeneratedValue()
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @Groups("bookitem")
     * @ORM\Column(type="integer")
     */
    private $state;

    /**
     * @Groups("bookitem")
     * @ORM\Column(type="string", length=255)
     */
    private $shelf;

    /**
     * @Groups("bookitem")
     * @ORM\Column(type="datetime")
     */
    private $created_at;

    /**
     * @Groups("bookitem")
     * @ORM\Column(type="datetime")
     */
    private $updated_at;

    /**
     * @Groups("bookitem")
     * @ORM\Column(type="boolean")
     */
    private $is_deleted;

    /**
     * @Groups("book_id")
     * @ORM\ManyToOne(targetEntity="App\Entity\Book", inversedBy="bookItems")
     * @ORM\JoinColumn(nullable=false)
     */
    private $book_id;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getState(): ?int
    {
        return $this->state;
    }

    public function setState(int $state): self
    {
        $this->state = $state;

        return $this;
    }

    public function getShelf(): ?string
    {
        return $this->shelf;
    }

    public function setShelf(string $shelf): self
    {
        $this->shelf = $shelf;

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

    public function getUpdatedAt(): ?\DateTimeInterface
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

    public function getBookId(): ?Book
    {
        return $this->book_id;
    }

    public function setBookId(?Book $book_id): self
    {
        $this->book_id = $book_id;

        return $this;
    }

}
