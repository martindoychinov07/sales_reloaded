/**
 * Copyright 2026 Martin Doychinov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

export type KeyCode =
// Letters
  | "KeyA" | "KeyB" | "KeyC" | "KeyD" | "KeyE" | "KeyF" | "KeyG"
  | "KeyH" | "KeyI" | "KeyJ" | "KeyK" | "KeyL" | "KeyM" | "KeyN"
  | "KeyO" | "KeyP" | "KeyQ" | "KeyR" | "KeyS" | "KeyT" | "KeyU"
  | "KeyV" | "KeyW" | "KeyX" | "KeyY" | "KeyZ"

  // Digits (top row)
  | "Digit0" | "Digit1" | "Digit2" | "Digit3" | "Digit4"
  | "Digit5" | "Digit6" | "Digit7" | "Digit8" | "Digit9"

  // Function keys
  | "F1" | "F2" | "F3" | "F4" | "F5" | "F6"
  | "F7" | "F8" | "F9" | "F10" | "F11" | "F12"

  // Modifiers
  | "ShiftLeft" | "ShiftRight"
  | "ControlLeft" | "ControlRight"
  | "AltLeft" | "AltRight"
  | "MetaLeft" | "MetaRight"
  | "CapsLock" | "NumLock" | "ScrollLock"

  // Arrows
  | "ArrowUp" | "ArrowDown" | "ArrowLeft" | "ArrowRight"

  // Whitespace and editing
  | "Enter" | "Tab" | "Space" | "Backspace" | "Delete" | "Insert"

  // Navigation
  | "Home" | "End" | "PageUp" | "PageDown"

  // Symbols
  | "Minus" | "Equal" | "BracketLeft" | "BracketRight"
  | "Backslash" | "Semicolon" | "Quote" | "Backquote"
  | "Comma" | "Period" | "Slash"

  // Numpad
  | "Numpad0" | "Numpad1" | "Numpad2" | "Numpad3" | "Numpad4"
  | "Numpad5" | "Numpad6" | "Numpad7" | "Numpad8" | "Numpad9"
  | "NumpadAdd" | "NumpadSubtract" | "NumpadMultiply" | "NumpadDivide"
  | "NumpadDecimal" | "NumpadEnter"

  // Misc controls
  | "Escape" | "PrintScreen" | "Pause" | "ContextMenu";
